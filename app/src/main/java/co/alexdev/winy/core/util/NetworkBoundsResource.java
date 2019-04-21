package co.alexdev.winy.core.util;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import co.alexdev.winy.core.api.ApiResponse;

public abstract class NetworkBoundsResource<ResultType, RequestType> {

    private final WinyExecutor mExecutor;
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundsResource(WinyExecutor mExecutor) {
        this.mExecutor = mExecutor;

        result.setValue(Resource.loading(null));
        LiveData<ResultType> databaseSource = loadFromDatabase();

        /*Starts to listen to the given source of the live data*/
        result.addSource(databaseSource, data -> {
            /*Set as the source for the live data the database source
             * After remove the source and check if we should fetch new data
             * If yes, fetch, else add the same database source */
            result.removeSource(databaseSource);

            /*If the input that we received from the Database is null, fetch new data from network
             * Note if its false start listening from the DB
             * Note on should fetch data, something might happen so we need to be aware of that
             * */
            if (shouldFetch(data)) {
                fetchFromNetwork(databaseSource);
            } else {
                result.addSource(databaseSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    /*Create a call
     * Start to listen to the result from the database and set the value to loading
     * Start to listen to the API CALL RESPONSE
     * STOP TO LISTEN TO THOSE CALLS
     * if the response is succesful, call savecallResult with the function processResponse
     * processResponse return response.body
     * After the call is saved (INSERTED TO DB), we start to fetch those data from the database, else means the response was not suffesull so we fetch from the db
     * */
    private void fetchFromNetwork(final LiveData<ResultType> databaseSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(databaseSource, newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(databaseSource);

            if (response.isSuccessful()) {
                mExecutor.getDiskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    mExecutor.getMainThread().execute(() ->
                            result.addSource(loadFromDatabase(),
                                    // we specially request a new live data,
                                    // otherwise we will get immediately last cached value,
                                    // which may not be updated with latest results received from network.
                                    newData -> result.setValue(Resource.success(newData))));
                });
            } else {
                onFetchFailed();
                result.addSource(databaseSource, newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@NonNull ResultType data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDatabase();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected void onFetchFailed() {
    }
}
