package org.config;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.config.util.ActionTypeFlag;
import org.config.util.CsvConfigMapper;


public final class IsoFieldValidator {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static final String EMPTY_STRING = "";

    private static IsoFieldValidator instance;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final Map<String, List<JurisdictionNode>> isoFieldMap;
    private final CsvConfigMapper csvConfigMapper;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    private IsoFieldValidator() {
        isoFieldMap = new ConcurrentHashMap<>();
        csvConfigMapper = new CsvConfigMapper();
        init();
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static IsoFieldValidator getInstance() {
        if (instance == null) {
            instance = new IsoFieldValidator();
        }
        return instance;
    }

    public Map<String, List<JurisdictionNode>> getIsoFieldMap() {
        return isoFieldMap;
    }

    public Boolean retrieveFlag(String fieldLabel, String jurisdiction, String actionType) {
        ActionTypeFlag flag = mapActionTypeToActionTypeFlag(actionType);
        //J-
        return Optional.ofNullable(isoFieldMap.get(fieldLabel))
                .flatMap(nodes -> nodes.stream()
                        .filter(jurisdictionNode -> jurisdictionNode.getJurisdiction().equals(jurisdiction))
                        .findFirst()
                        .map(jurisdictionNode -> jurisdictionNode.getJurisdictionFlagValue(flag)))
                .orElse(null);
        //J+
    }

    private void init() {
        //J-
        IntStream.range(csvConfigMapper.getCsvParser().getHeadersLineIndex() + 1, csvConfigMapper.getCsvParser().getLinesFromCsv().size())
                .forEach(i -> addToIsoFieldMap(csvConfigMapper.getFieldLabelFromIndex(i), csvConfigMapper.getJurisdictionNodesFromIndex(i))
                );
        //J+
    }

    private void addToIsoFieldMap(String fieldLabel, List<JurisdictionNode> jurisdictionNodes) {
        this.isoFieldMap.put(fieldLabel, jurisdictionNodes);
    }

    private ActionTypeFlag mapActionTypeToActionTypeFlag(String actionType) {
        //J-
        switch (Optional.ofNullable(actionType).orElse(EMPTY_STRING)) {
            case "TERM":
                return ActionTypeFlag.TERM;
            case "EROR":
                return ActionTypeFlag.EROR;
            case "VALU":
                return ActionTypeFlag.VALU;
            case "MARU":
                return ActionTypeFlag.MARU;
            case "CORR":
                return ActionTypeFlag.CORR;
            case EMPTY_STRING:
                return null;
            default:
                return ActionTypeFlag.NRMC;
        }
        //J+
    }

}
