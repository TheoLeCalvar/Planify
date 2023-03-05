package com.solver.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.solver.model.Donnee;
import com.solver.model.Modelisation;
import com.solver.model.Module;
import com.solver.model.Request;
import com.solver.model.User;
import com.solver.util.Constants;
import com.solver.util.Localisation;

@Service
public class SolverService {

	@Autowired
	protected RestTemplate restTemplate;

	public String solver(Request request) throws IOException {
		Donnee data = new Donnee(request.getModulesUeA(), request.getModulesUeB(), request.getModulesUeC(),
				request.getWeeksNumber(), request.getUnavailabilities().get(Localisation.Nantes),
				request.getUnavailabilities().get(Localisation.Brest), request.getStartDate());

		Map<String, User> userList = new HashMap<>();
		for (Module module : data.getListe_Module()) {
			if (module != null) {
				module.getMails().values().forEach((mail) -> {
					User user = restTemplate.getForEntity(Constants.getUrlUser() + "/" + mail, User.class).getBody();
					user.setUnavailabilitiesTraduction(data.Traduction(user.getUnavailabilities()));
					userList.put(mail, user);
				});
			}
		}

		Modelisation test = new Modelisation(data, userList);
		test.BuildModel();
		test.addConstraints();
		test.solve();

		String fileName = request.getCreationDate() + ".csv";
		test.ecrire(fileName);
		return fileName;
	}

}
