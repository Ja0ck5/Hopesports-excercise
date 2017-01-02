package cn.lyj.core.dao.user;

import java.util.List;

import cn.lyj.core.bean.user.Employee;
import cn.lyj.core.query.user.EmployeeQuery;

public interface EmployeeDao {

	/**
	 * 添加
	 * @param employee
	 */
	public Integer addEmployee(Employee employee);

	/**
	 * 根据主键查找
	 * @param employeeQuery
	 */
	public Employee getEmployeeByKey(String id);

	/**
	 * 根据主键批量查找
	 * @param employeeQuery
	 */
	public List<Employee> getEmployeesByKeys(List<String> idList);

	/**
	 * 根据主键删除
	 * @param employeeQuery
	 */
	public Integer deleteByKey(String id);

	/**
	 * 根据主键批量删除
	 * @param employeeQuery
	 */
	public Integer deleteByKeys(List<String> idList);

	/**
	 * 根据主键更新
	 * @param employeeQuery
	 */
	public Integer updateEmployeeByKey(Employee employee);

	/**
	 * 分页查询
	 * @param employeeQuery
	 */
	public List<Employee> getEmployeeListWithPage(EmployeeQuery employeeQuery);

	/**
	 * 集合查询
	 * @param employeeQuery
	 */
	public List<Employee> getEmployeeList(EmployeeQuery employeeQuery);
	
	/**
	 * 总条数
	 * @param employeeQuery
	 */
	public int getEmployeeListCount(EmployeeQuery employeeQuery);
}
