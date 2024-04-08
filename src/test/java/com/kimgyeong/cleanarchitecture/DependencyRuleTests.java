package com.kimgyeong.cleanarchitecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.importer.ClassFileImporter;

public class DependencyRuleTests {
	@Test
	@DisplayName("Domain 계층에서 Application 계층으로 향하는 의존성이 없다")
	void domainLayerDoesNotDependOnApplicationLayer() {
		noClasses()
			.that()
			.resideInAPackage("cleanarchitecture.domain..")
			.should()
			.dependOnClassesThat()
			.resideInAnyPackage("cleanarchitecture.application..")
			.check(new ClassFileImporter()
				.importPackages("cleanarchitecture.."));
	}
}
