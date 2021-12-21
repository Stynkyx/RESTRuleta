package com.ibm.academia.apirest.mapper;

import com.ibm.academia.apirest.models.dto.RuletaDTO;
import com.ibm.academia.apirest.models.entities.Ruleta;

public class RuletaMapper {

	public static RuletaDTO mapRuleta(Ruleta ruleta) {
		RuletaDTO ruletaDTO = new RuletaDTO();
		ruletaDTO.setId(ruleta.getId());
		if(ruleta.isEstadoRuleta())
			ruletaDTO.setEstadoRuleta("abierta");
		else
			ruletaDTO.setEstadoRuleta("cerrada");
		return ruletaDTO;
	}
}
