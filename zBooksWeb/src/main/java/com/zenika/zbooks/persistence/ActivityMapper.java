package com.zenika.zbooks.persistence;

import com.zenika.zbooks.entity.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityMapper {

    List<Activity> getActivities();

    List<Activity> getActivitiesOfType(@Param("type") String type,
                                       @Param("sortBy") String sortBy,
                                       @Param("order") String order);

    Activity getActivity(int id);

    void addActivity(Activity activity);

}
