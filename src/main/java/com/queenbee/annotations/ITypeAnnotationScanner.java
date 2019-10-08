package com.queenbee.annotations;

public interface ITypeAnnotationScanner extends IAnnotationScanner {
    @Override
    void scan();
    String[] getAllClases();
}
