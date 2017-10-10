package com.portfolio.elkeno_jones.mindgamesmaven.util;

import com.portfolio.elkeno_jones.mindgamesmaven.model.Feature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elkeno
 */
public class FeatureUtil {
    /**
     * Method translates a list of features to a map.
     * Key: id
     * value: descriptionLong
     * 
     * @param feats
     * @return 
     */
    public static Map<Integer, String> mapIdToFeature(List<Feature> feats) {
        Map<Integer, String> featMap = new HashMap<Integer, String>();
        for (Feature feat : feats) {
            if (feat.getFeatureId() != null) {
                featMap.put(feat.getFeatureId(), feat.getDescriptionLong());
            }
        }

        return featMap;
    }
}
