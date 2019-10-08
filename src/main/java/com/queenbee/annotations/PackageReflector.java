package com.queenbee.annotations;

import com.queenbee.controllers.Controller;
import com.queenbee.models.Model;
import com.queenbee.views.View;

import java.util.ArrayList;
import java.util.List;

public class PackageReflector implements IPackageReflector{
    private IMethodAnnotationScanner methodAnnotationScanner;
    private ITypeAnnotationScanner typeAnnotationScanner;

    public void addMethodScanner(IMethodAnnotationScanner scanner) {
        this.methodAnnotationScanner = scanner;
    }
    public void addTypeScanner(ITypeAnnotationScanner scanner) {
        this.typeAnnotationScanner = scanner;
    }
    @Override
    public Class<? extends Model>[] getAllModels() {
        return new Class[0];
    }

    @Override
    public Class<? extends View>[] getAllViews() {
        return new Class[0];
    }

    @Override
    public Class<? extends Controller>[] getAllControllers() {
        return new Class[0];
    }
}
