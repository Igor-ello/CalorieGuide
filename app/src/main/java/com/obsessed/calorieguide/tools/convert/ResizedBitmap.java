package com.obsessed.calorieguide.tools.convert;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ResizedBitmap {
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Создаем матрицу для масштабирования
        Matrix matrix = new Matrix();

        // Масштабируем изображение с матрицей
        matrix.postScale(scaleWidth, scaleHeight);

        // Пересоздаем изображение с новыми размерами
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }
}
