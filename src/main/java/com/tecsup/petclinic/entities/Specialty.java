package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jgomezm
 *
 */
@Entity(name = "specialties")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Specialty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String office;

	@Column(name = "h_open")
	private Integer hOpen;

	@Column(name = "h_close")
	private Integer hClose;
}
