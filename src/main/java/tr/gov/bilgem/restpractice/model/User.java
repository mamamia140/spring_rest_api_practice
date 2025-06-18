package tr.gov.bilgem.restpractice.model;

/**
 * Represents application (API) user account.
 *
 * @author Serdar Serpen
 * @date Nov 3, 2023
 * @since 1.0.0
 */
//@Entity
class User {

	public enum Role {
		ADMIN, OPERATOR, OBSERVER
	}

	private Long id;

	// Not blank
	// Unique
	// Max 50
	private String username;

	// Not blank
	// Unique
	// Email
	// Max 50
	private String email;

	// Not blank
	// Min 6
	// Max 50
	private String password;

	// Not null
	private Role role;

}
