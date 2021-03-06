package org.benetech.servicenet.conflict.detector;

import org.apache.commons.lang3.StringUtils;
import org.benetech.servicenet.domain.Conflict;
import org.benetech.servicenet.domain.enumeration.ConflictStateEnum;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDetector<T> {

    protected boolean areEquals(String current, String offered) {
        return StringUtils.equalsIgnoreCase(current, offered) ||
            (StringUtils.isBlank(current) && StringUtils.isBlank(offered));
    }

    protected List<Conflict> detectConflicts(T current, String name, String name2, String fieldName) {
        List<Conflict> conflicts = new LinkedList<>();
        if (!this.areEquals(name, name2)) {
            conflicts.add(createConflict(current, name, name2, fieldName));
        }
        return conflicts;
    }

    protected<Y> List<Conflict> detectConflicts(T current, Y val, Y val2, String fieldName) {
        List<Conflict> conflicts = new LinkedList<>();
        if (notEquals(val, val2)) {
            conflicts.add(createConflict(current, val, val2, fieldName));
        }
        return conflicts;
    }

    protected<Y> Conflict createConflict(T obj, Y currentValue, Y offeredValue, String fieldName) {
        return Conflict.builder()
            .currentValue(getString(currentValue))
            .offeredValue(getString(offeredValue))
            .fieldName(fieldName)
            .entityPath(obj.getClass().getCanonicalName())
            .state(ConflictStateEnum.PENDING)
            .stateDate(ZonedDateTime.now())
            .createdDate(ZonedDateTime.now())
            .build();
    }

    private<Y> boolean notEquals(Y obj, Y obj2) {
        return (obj != null && !obj.equals(obj2)) || (obj2 != null && !obj2.equals(obj));
    }

    private<Y> String getString(Y val) {
        if (val == null) {
            return StringUtils.EMPTY;
        } else {
            return val.toString();
        }
    }

}
