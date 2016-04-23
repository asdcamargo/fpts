package com.finance.company.journal.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// SSL
	// @Bean
	// public EmbeddedServletContainerCustomizer
	// containerCustomizer(@Value("${keystore.file}") String keystoreFile,
	// @Value("${keystore.password}") String keystorePassword,
	// @Value("${keystore.type}") String keystoreType,
	// @Value("${keystore.alias}") String keystoreAlias) throws
	// FileNotFoundException {
	// final String absoluteKeystoreFile =
	// ResourceUtils.getFile(keystoreFile).getAbsolutePath();
	//
	// return (ConfigurableEmbeddedServletContainer factory) -> {
	// TomcatEmbeddedServletContainerFactory containerFactory =
	// (TomcatEmbeddedServletContainerFactory) factory;
	// containerFactory.addConnectorCustomizers((TomcatConnectorCustomizer)
	// (Connector connector) -> {
	// connector.setSecure(true);
	// connector.setScheme("https");
	// connector.setPort(8443);
	// connector.setAttribute("keystoreFile", absoluteKeystoreFile);
	// connector.setAttribute("keystorePass", keystorePassword);
	// connector.setAttribute("keystoreType", keystoreType);
	// connector.setAttribute("keyAlias", keystoreAlias);
	// connector.setAttribute("clientAuth", "false");
	// connector.setAttribute("sslProtocol", "TLS");
	// connector.setAttribute("SSLEnabled", true);
	// });
	// };
	// }

}
