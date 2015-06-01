package com.mahadevanr.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import com.mahadevanr.app.AppContext;

@Entity
@Table
public class UserRole implements Serializable {

	@Id
	@GeneratedValue
	private Long useRoleId;

	@ManyToOne
	private User user;

	private boolean isSuppressed;

	private String roleName;

	public UserRole() {

	}

	public User getDepartment() {
		return user;
	}

	public void setDepartment(User user) {
		this.user = user;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isSuppressed() {
		return isSuppressed;
	}

	public void setSuppressed(boolean isSuppressed) {
		this.isSuppressed = isSuppressed;
	}

	/**
	 * This Method Returns listOf <UserRole> for Active <User>
	 */
	public synchronized static List<UserRole> userRolesListOf() {
		Criteria roles = AppContext.getContext().createCriteria(UserRole.class,
				"userroles");
		DetachedCriteria users = DetachedCriteria.forClass(User.class, "users");
		users.add(Restrictions.eq("isSuppressed", true));
		users.add(Property.forName("users.userId").eqProperty(
				"userRoles.userId"));
		roles.add(Subqueries.exists(users.setProjection(Projections
				.property("userId"))));
		return roles.list();
	}

}
