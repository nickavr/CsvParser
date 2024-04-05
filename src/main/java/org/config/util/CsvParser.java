package org.config.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.IntStream.range;

import org.exception.ReadIsoCsvException;


public final class CsvParser {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final int KEEP_TRAILING_EMPTY_VALUES_FLAG = -1;
    private static final String SEMI_COLON = ";";
    private static final String CSV_CONFIG_FILE_ABSOLUT_PATH = "/isoFieldsConfig.csv";

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final List<String> linesFromCsv;
    private final int headersLineIndex;
    private final List<String> headers;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public CsvParser() {
        linesFromCsv = splitCsvToLines();
        headersLineIndex = extractIndexHeaderline();
        headers = computeHeadersLine();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public List<String> getLinesFromCsv() {
        return linesFromCsv;
    }

    public int getHeadersLineIndex() {
        return headersLineIndex;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public LinkedList<String> splitCsvLineToValues(int index) {
        String csvLine = linesFromCsv.get(index);
        //J-
        return Arrays.stream(csvLine.trim().split(SEMI_COLON, KEEP_TRAILING_EMPTY_VALUES_FLAG))
                .collect(Collectors.toCollection(LinkedList::new));
        //J+
    }

    private List<String> splitCsvToLines() {
        try(InputStream is = CsvConfigMapper.class.getResourceAsStream(CSV_CONFIG_FILE_ABSOLUT_PATH)) {
            //J-
            return Optional.ofNullable(is)
                    .map(stream -> new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.toList()))
                    .orElseThrow(() ->
                            new ReadIsoCsvException("Iso CSV path is not correct")
                    );
            //J+
        } catch (IOException e) {
            throw new ReadIsoCsvException(e.getMessage());
        }
    }

    private int extractIndexHeaderline() {
        //J-
        return range(0, linesFromCsv.size())
                .filter(i -> linesFromCsv.get(i).contains("Field label"))
                .findFirst()
                .orElse(0);
        //J+
    }

    private List<String> computeHeadersLine() {
        return splitCsvLineToValues(headersLineIndex);
    }

}
