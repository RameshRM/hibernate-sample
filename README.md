# hibernate-sample
Very simple project to demonstrate `Exists Clause` with Hibernate `Criteria && DetachedCriteria Queries`

## Installation

``
git clone https://github.com/RameshRM/hibernate-sample.git
``

## Run

``
mvn install
``
> This is a `maven` project, the dependencies are maitained @ [`pom.xml`](https://github.com/RameshRM/hibernate-sample/blob/master/pom.xml#L22) you dont need any JARs in your bin or in the classpath

### Install maven

## Example

```Java
	/**
	 * This Method Returns listOf UserRoles For All Active Users
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
```

The above query is as good as 

```SQL

	SELECT * 
	FROM UserRoles UR
	WHERE EXISTS (SELECT 1 FROM User U WHERE U.IsSuppressed = 0 AND WHERE UR.UserId = U.UserId)
```

> This example is just a How to , The DB connection and the tables are fictious.




