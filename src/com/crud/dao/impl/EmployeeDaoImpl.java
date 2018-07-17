package com.crud.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.crud.dao.EmployeeDao;
import com.crud.model.Employee;

/*@Transactional*/
public class EmployeeDaoImpl implements EmployeeDao {

	private HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@Override
	public void insertEmployee(Employee employee) {
		hibernateTemplate.save(employee);
		
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		return hibernateTemplate.get(Employee.class, employeeId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmployees() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		return (List<Employee>) hibernateTemplate.findByCriteria(criteria);
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		hibernateTemplate.delete(employee);
	}

	@Override
	public void updateEmployeeMail(String email, int employeeId) {
		Employee employee = hibernateTemplate.get(Employee.class, employeeId);
		employee.setEmail(email);
		hibernateTemplate.update(employee);
		
	}

	
	
	
	
	
	// using session factory of hibernate and using transactional annotation and hibernate transaction manager
	
	/*@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            throw new IllegalStateException("SessionFactory has not been set on DAO before usage");
        return sessionFactory;
    }
	
	@Override
	public void insertEmployee(Employee employee) {
		
		try {
			System.out.println(sessionFactory+"\t"+sessionFactory.getCurrentSession());
			getSessionFactory().getCurrentSession().save(employee);
			System.out.println("Employee object inserted successfully");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Employee employee = null;
		try {
			employee = (Employee)sessionFactory.getCurrentSession().get(Employee.class, employeeId);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllEmployees() {
		return sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		//Employee employee = (Employee)sessionFactory.getCurrentSession().get(Employee.class, employeeId);
		try {
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			sessionFactory.getCurrentSession().delete(employee);
			System.out.println("employee deleted with id::"+employeeId);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void updateEmployeeMail(String email, int employeeId) {
		try {
			Employee employee = (Employee)sessionFactory.getCurrentSession().get(Employee.class, employeeId);
			employee.setEmail(email);
			sessionFactory.getCurrentSession().update(employee);
			System.out.println("employee email updated with id::"+employeeId);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	
	
	// using jdbc Template
	/*private JdbcTemplate JdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		JdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertEmployee(Employee employee) {
		
		String SQL = "insert into employee_table(employee_name, email, sal, dept_no) values(?,?,?,?)";
		int insert = JdbcTemplate.update(SQL, employee.getEmployeeName(),employee.getEmail(),employee.getSalary(),employee.getDeptNum());
		if(insert > 0) {
			System.out.println("Employee object inserted successfully...");
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		String SQL = "select * from employee_table where employee_id=?";
		return JdbcTemplate.queryForObject(SQL, new EmployeeRowMapper(), employeeId);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return JdbcTemplate.query("select * from employee_table", new EmployeeRowMapper());
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		String SQL = "delete from employee_table where employee_id=?";
		int delete = JdbcTemplate.update(SQL, employeeId);
		if(delete > 0) {
			System.out.println("Employee deleted with id::"+employeeId);
		}
	}

	@Override
	public void updateEmployeeMail(String email, int employeeId) {
		String SQL = "update employee_table set email=? where employee_id=?";
		int update = JdbcTemplate.update(SQL, email,employeeId);
		if(update > 0) {
			System.out.println("emoloyee email updated with id::"+employeeId );
		}
	}*/
	
	
	// using dataSource with simple jdbc code
	
	/*private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertEmployee(Employee employee) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String SQL = "insert into employee_table(employee_name, email, sal, dept_no) values(?,?,?,?)";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, employee.getEmployeeName());
			pstmt.setString(2, employee.getEmail());
			pstmt.setDouble(3, employee.getSalary());
			pstmt.setInt(4, employee.getDeptNum());
			
			int insert = pstmt.executeUpdate();
			if(insert > 0) {
				System.out.println("Employee object inserted successfully...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Employee employee = null;
		try {
			conn = dataSource.getConnection();
			String SQL = "select * from employee_table where employee_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, employeeId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employee_id"));
				employee.setEmployeeName(rs.getString("employee_name"));
				employee.setEmail(rs.getString("email"));
				employee.setSalary(rs.getDouble("sal"));
				employee.setDeptNum(rs.getInt("dept_no"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return employee;
	}

	@Override
	public List<Employee> getAllEmployees() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Employee employee = null;
		List<Employee> list = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			String SQL = "select * from employee_table";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while(rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employee_id"));
				employee.setEmployeeName(rs.getString("employee_name"));
				employee.setEmail(rs.getString("email"));
				employee.setSalary(rs.getDouble("sal"));
				employee.setDeptNum(rs.getInt("dept_no"));
				list.add(employee);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String SQL = "delete from employee_table where employee_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, employeeId);
			
			int delete = pstmt.executeUpdate();
			if(delete > 0) {
				System.out.println("Employee object deleted successfully with employeeId::"+employeeId);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void updateEmployeeMail(String email, int employeeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String SQL = "update employee_table set email=? where employee_id=?";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, email);
			pstmt.setInt(2, employeeId);
			
			int update = pstmt.executeUpdate();
			if(update > 0) {
				System.out.println("Employee email is updated successfully with id:"+employeeId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

}
