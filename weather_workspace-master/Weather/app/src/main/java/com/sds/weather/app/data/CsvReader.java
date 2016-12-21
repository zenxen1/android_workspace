package com.sds.weather.app.data;

import android.content.Context;
import android.util.Log;

import com.sds.weather.app.R;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created on 2016-11-30.
 */

public class CsvReader {
    private Context context;

    // Stream
    private BufferedReader bufferedReader;

    public CsvReader(Context context) {
        this.context = context;
    }

    public List<CSVRecord> readCSV() {
        List<CSVRecord> records = null;

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.location);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            records = CSVFormat.EXCEL.parse(bufferedReader).getRecords();
        } catch (UnsupportedEncodingException e) {
            Log.e(getClass().getName(), "올바르지 않은 인코딩 형식입니다." + e.getMessage());
        } catch (IOException e) {
            Log.e(getClass().getName(), "CSV 파일을 parsing 하는데 실패하였습니다." + e.getMessage());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return records;
        }

    }
}
