package com.obsessed.calorieguide.tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    private static Algorithm instance;

    public static Algorithm getInstance() {
        if (instance == null) {
            instance = new Algorithm();
        }
        return instance;
    }

    public static boolean isSimilar(String str1, String str2, int maxDifference) {
        // Преобразуем строки к нижнему регистру для регистронезависимого сравнения
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        //Если одна из строк содержит другую, возвращаем true
        if (str1.contains(str2) || str2.contains(str1)) {
            return true;
        }

        int differences1 = 0;
        int length = Math.min(str1.length(), str2.length());
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                differences1++;
                if (differences1 > maxDifference) {
                    break;
                }
            }
        }

        // Считаем количество несовпадающих символов
        int differences2 = 0;
        int length1 = str1.length();
        int length2 = str2.length();
        int i = 0, j = 0;
        while (i < length1 && j < length2) {
            // Если текущие символы совпадают, продолжаем поиск следующих подряд идущих символов
            if (str1.charAt(i) == str2.charAt(j)) {
                int consecutiveMatchLength = 1;
                // Считаем количество подряд идущих совпадающих символов
                while (i + consecutiveMatchLength < length1 && j + consecutiveMatchLength < length2 &&
                        str1.charAt(i + consecutiveMatchLength) == str2.charAt(j + consecutiveMatchLength)) {
                    consecutiveMatchLength++;
                }
                // Если количество подряд идущих совпадающих символов превышает порог, уменьшаем разницу
                if (consecutiveMatchLength > maxDifference + 1) {
                    differences2 -= consecutiveMatchLength - (maxDifference + 1);
                }
                i += consecutiveMatchLength;
                j += consecutiveMatchLength;
            } else {
                differences2++;
                if (differences2 > maxDifference) {
                    break;
                }
                // Если символы не совпадают, переходим к следующим символам
                i++;
                j++;
            }
        }

        // Если количество несовпадающих символов не превышает порог, считаем строки похожими
        return Math.min(differences1, differences2) <= maxDifference;
    }
}
