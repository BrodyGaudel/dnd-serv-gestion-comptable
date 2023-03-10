package com.dndserv.brody.gestioncomptable.security;


import com.dndserv.brody.gestioncomptable.entities.User;
import com.dndserv.brody.gestioncomptable.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);

		if (user == null){
			throw new UsernameNotFoundException("Utilisateur introuvable !");
		}else{
			List<GrantedAuthority> auths = new ArrayList<>();

			user.getRoles().forEach(role -> {
				GrantedAuthority auhority = new SimpleGrantedAuthority(role.getRoleName());
				auths.add(auhority);
			});

			return new org.springframework.security.core.
					userdetails.User(user.getLogin(),user.getPassword(),auths);
		}
	}
}
