package org.config.util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.IntStream.range;

import org.config.JurisdictionNode;


public final class CsvConfigMapper {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static final int RANGE_BETWEEN_JURISDICTIONS = 6;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final CsvParser csvParser;
    public final int noOfJurisdictions;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public CsvConfigMapper() {
        csvParser = new CsvParser();
        noOfJurisdictions = computeNoOfJurisdictions();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public CsvParser getCsvParser() {
        return csvParser;
    }

    public String getFieldLabelFromIndex(int index) {
        return csvParser.splitCsvLineToValues(index).get(0);
    }

    public List<JurisdictionNode> getJurisdictionNodesFromIndex(int index) {
        LinkedList<String> currentLineValues = csvParser.splitCsvLineToValues(index);
        currentLineValues.remove(csvParser.getHeadersLineIndex());

        //J-
        return range(0, noOfJurisdictions)
                .mapToObj(i -> {
                    JurisdictionNode jurisdictionNode = new JurisdictionNode();
                    jurisdictionNode.setJurisdiction(extractJurisdictionName(csvParser.getHeaders().get(i * RANGE_BETWEEN_JURISDICTIONS + 1)));

                    Boolean[] flags = range(i * RANGE_BETWEEN_JURISDICTIONS, i * RANGE_BETWEEN_JURISDICTIONS + RANGE_BETWEEN_JURISDICTIONS)
                            .mapToObj(j -> BooleanUtil.parseBoolean(currentLineValues.get(j)))
                            .toArray(Boolean[]::new);

                    jurisdictionNode.setFlags(flags);

                    return jurisdictionNode;
                }).collect(Collectors.toList());
        //J+
    }

    private int computeNoOfJurisdictions() {
        return (csvParser.getHeaders().size() - 1) / RANGE_BETWEEN_JURISDICTIONS;
    }

    private String extractJurisdictionName(String input) {
        return input.substring(0, input.indexOf("-"));
    }
}
