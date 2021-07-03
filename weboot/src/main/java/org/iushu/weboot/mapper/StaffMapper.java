package org.iushu.weboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.iushu.weboot.bean.Staff;

/**
 * @author iuShu
 * @since 6/24/21
 */
@Mapper
public interface StaffMapper {

    @Select("SELECT staff_id, first_name, last_name, address_id, picture, email, store_id, active, username, password, last_update " +
            "FROM sakila.staff WHERE staff_id = #{staff_id} ")
    Staff getStaff(short staff_id);

    @Select("SELECT staff_id, first_name, last_name, address_id, picture, email, store_id, active, username, password, last_update " +
            "FROM sakila.staff WHERE username = #{username} ")
    Staff getStaffByUserName(String username);

}
