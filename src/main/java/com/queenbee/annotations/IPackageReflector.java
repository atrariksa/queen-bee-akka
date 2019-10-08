package com.queenbee.annotations;

import com.queenbee.controllers.Controller;
import com.queenbee.models.Model;
import com.queenbee.views.View;

public interface IPackageReflector {
    Class<? extends Model>[] getAllModels();
    Class<? extends View>[] getAllViews();
    Class<? extends Controller>[] getAllControllers();
}
