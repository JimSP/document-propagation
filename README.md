# document-propagation
propagação de contratos do swagger em um cluster hazelcast

### Gradle
Exemplo para propagar APIs de microserviço no cluster:

    dependencies {
        ...
	    compile project(':document-propagation-spring-boot-starter')
	    
	    ...
    }

### Habilitando o cluster
Anotação para habilitar as features de propagação de contratos do swagger:

    @EnableDocumentPropagation

O uso dessa anotação irá gerar automaticamente os contratos do swagger e propagar no cluster.
Esses contratos ficarão disponíveis no modulo **document-propagation-server**

#### exemplo:

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.http.MediaType;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.RestController;
    
    import br.com.cafebinario.documentpropagation.annotations.EnableDocumentPropagation;
    
    @SpringBootApplication
    @EnableDocumentPropagation
    @RestController
    public class DocumentPropagationExampleHelloApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(DocumentPropagationExampleHelloApplication.class, args);
        }
	
        @GetMapping(path="/hello", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
        public @ResponseBody String value() {
            return "hello";
        }
    }

### Acessando os contratos centralizados:
Para acessar os contratos disponíveis no cluster de forma centralizada, basta iniciar o server **document-propagation-server** e acessar:

**path swagger-ui** 
- /swagger-ui.html

#### exemplo:
    http://localhost:8000/swagger-ui.html

### Script para montar ambiente de exemplo:
    #!/usr/bin/env sh
    
    ##############################################################################
    ##
    ##  document-propagation start up script for UN*X
    ##
    ##############################################################################
    
    ##clone project
    git clone https://github.com/JimSP/document-propagation.git
    
    ##start microservice hello
    cd document-propagation/document-propagation-example-hello
    nohup sh -x ./gradlew bootRun &
    
    ##start microservice world
    cd ../document-propagation-example-world
    nohup sh -x ./gradlew bootRun &
    
    ##start server
    cd ../document-propagation-server
    nohup sh -x ./gradlew bootRun &
    
    ### open browser
    x-www-browser http://localhost:8000/swagger-ui.html