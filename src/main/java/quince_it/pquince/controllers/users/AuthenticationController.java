package quince_it.pquince.controllers.users;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import quince_it.pquince.entities.users.User;
import quince_it.pquince.security.TokenUtils;
import quince_it.pquince.security.auth.JwtAuthenticationRequest;
import quince_it.pquince.security.exception.ResourceConflictException;
import quince_it.pquince.services.contracts.dto.users.UserDTO;
import quince_it.pquince.services.contracts.dto.users.UserRequestDTO;
import quince_it.pquince.services.contracts.dto.users.UserTokenStateDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.implementation.users.AuthorityService;
import quince_it.pquince.services.implementation.users.CustomUserDetailsService;
import quince_it.pquince.services.implementation.users.UserService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;


	@PostMapping("/login")
	@CrossOrigin
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		String jwt;
		int expiresIn;
		List<String> roles = new ArrayList<String>();
		
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
			
	
			SecurityContextHolder.getContext().setAuthentication(authentication);
			User user = (User) authentication.getPrincipal();
			jwt = tokenUtils.generateToken(user.getUsername());
			expiresIn = tokenUtils.getExpiredIn();
			user.getUserAuthorities().forEach((a) -> roles.add(a.getName()));

		} catch (BadCredentialsException e) {
			
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<UserTokenStateDTO>(new UserTokenStateDTO(jwt, expiresIn, roles), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<UUID> addUser(@RequestBody UserRequestDTO userRequest) {
		try {
			UUID userId = userService.createPatient(userRequest);
			return new ResponseEntity<UUID>(userId, HttpStatus.CREATED);
		} catch (ResourceConflictException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signup-dermathologist")
	public ResponseEntity<UUID> addDermathologist(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {

		IdentifiableDTO<UserDTO> existUser = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getEmail(), "Email already exists");
		}

		UUID userId = userService.createDermatologist(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	@PostMapping("/signup-pharmacyadmin")
	public ResponseEntity<UUID> addPharmacyAdmin(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {

		IdentifiableDTO<UserDTO> existUser = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getEmail(), "Email already exists");
		}

		UUID userId = userService.createPharmacyAdmin(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	@PostMapping("/signup-sysadmin")
	public ResponseEntity<UUID> addSysadmin(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {

		IdentifiableDTO<UserDTO> existUser = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getEmail(), "Email already exists");
		}

		UUID userId = userService.createAdmin(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	@PostMapping("/signup-supplier")
	public ResponseEntity<UUID> addSupplier(@RequestBody UserRequestDTO userRequest, UriComponentsBuilder ucBuilder) {

		IdentifiableDTO<UserDTO> existUser = this.userService.findByEmail(userRequest.getEmail());
		if (existUser != null) {
			throw new ResourceConflictException(userRequest.getEmail(), "Email already exists");
		}

		UUID userId = userService.createSupplier(userRequest);
		
		return new ResponseEntity<>(userId, HttpStatus.CREATED);
	}
	
	@PostMapping("/change-password")
	@PreAuthorize("hasRole('PATIENT')") // or hasRole....
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		
		try {
		userService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
	

	// VRV NE TREBA METODA
	/*
	 * @PostMapping(value = "/refresh") public ResponseEntity<UserTokenStateDTO>
	 * refreshAuthenticationToken(HttpServletRequest request) {
	 * 
	 * String token = tokenUtils.getToken(request); String username =
	 * this.tokenUtils.getUsernameFromToken(token); User user = (User)
	 * this.userDetailsService.loadUserByUsername(username);
	 * 
	 * String role =
	 * user.getUserAuthorities().get(0).getName();//user.getUserAuthorities().get(0)
	 * .getName();
	 * 
	 * if (this.tokenUtils.canTokenBeRefreshed(token, new Date())) { String
	 * refreshedToken = tokenUtils.refreshToken(token); int expiresIn =
	 * tokenUtils.getExpiredIn();
	 * 
	 * return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken,
	 * expiresIn,role)); } else { UserTokenStateDTO userTokenState = new
	 * UserTokenStateDTO(); return ResponseEntity.badRequest().body(userTokenState);
	 * } }
	 */

}