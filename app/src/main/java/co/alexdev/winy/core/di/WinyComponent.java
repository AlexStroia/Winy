package co.alexdev.winy.core.di;

import javax.inject.Singleton;

import co.alexdev.winy.core.di.module.AnalyticsModule;
import co.alexdev.winy.core.di.module.DatabaseModule;
import co.alexdev.winy.core.di.module.ServiceModule;
import co.alexdev.winy.core.di.module.UserCredentialModule;
import co.alexdev.winy.core.model.user.UserCredential;
import co.alexdev.winy.feature.ui.detail.DetailActivity;
import co.alexdev.winy.feature.ui.favorite.FavoriteFragment;
import co.alexdev.winy.feature.ui.login.ActivityLogin;
import co.alexdev.winy.feature.ui.product.dish.DishFragment;
import co.alexdev.winy.feature.ui.product.wine.WineFragment;
import co.alexdev.winy.feature.ui.search.SearchActivity;
import dagger.Component;

@Singleton
@Component(modules = {UserCredentialModule.class, DatabaseModule.class, ServiceModule.class, AnalyticsModule.class})
public interface WinyComponent {

    UserCredential userCredential();

    void inject(ActivityLogin activityLogin);

    void inject(WineFragment wineFragment);

    void inject(DetailActivity detailActivity);

    void inject(SearchActivity searchActivity);

    void inject(FavoriteFragment favoriteFragment);

    void inject(DishFragment dishFragment);
}
