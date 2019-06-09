package co.alexdev.winy.core.di;


import co.alexdev.winy.core.di.module.AnalyticsModule;
import co.alexdev.winy.core.di.module.AuthRepoModule;
import co.alexdev.winy.core.di.module.DatabaseModule;
import co.alexdev.winy.core.di.module.PreferenceModule;
import co.alexdev.winy.core.di.module.ServiceModule;
import co.alexdev.winy.core.di.module.UserCredentialModule;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.core.repository.AuthenticationRepository;
import co.alexdev.winy.core.util.AnalyticsManager;
import co.alexdev.winy.core.util.WinyWidgetService;
import co.alexdev.winy.feature.ui.account.AccountActivity;
import co.alexdev.winy.feature.ui.detail.DetailActivity;
import co.alexdev.winy.feature.ui.favorite.FavoriteFragment;
import co.alexdev.winy.feature.ui.login.ActivityLogin;
import co.alexdev.winy.feature.ui.product.dish.DishFragment;
import co.alexdev.winy.feature.ui.product.wine.WineFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;
import dagger.Component;

@SingletoneScope
@Component(modules = {UserCredentialModule.class, DatabaseModule.class, ServiceModule.class, AnalyticsModule.class, PreferenceModule.class,
        AuthRepoModule.class})
public interface WinyComponent {

    void inject(ActivityLogin activityLogin);

    void inject(WineFragment wineFragment);

    void inject(DetailActivity detailActivity);

    void inject(SearchActivity searchActivity);

    void inject(FavoriteFragment favoriteFragment);

    void inject(DishFragment dishFragment);

    void inject(WinyWidgetService winyWidgetService);

    void inject(AccountActivity accountActivity);

    AuthenticationRepository provideAuthRepository();

    AnalyticsManager provideAnalyticsManager();
}
