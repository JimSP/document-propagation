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

### Script criar imagens docker
#!/usr/bin/env sh

## build project
    ./gradlew document-propagation-server:distDocker
    ./gradlew document-propagation-example-hello:distDocker
    ./gradlew document-propagation-example-world:distDocker

### Script para instalar e executar microservices
    #!/usr/bin/env sh
    
    ##pull microservices
    sudo docker pull cafebinario/document-propagation-example-server
    sudo docker pull cafebinario/document-propagation-example-hello
    sudo docker pull cafebinario/document-propagation-example-world
    
    ##start microservices
    sudo docker-compose up -d
    
    ### open browser
    x-www-browser http://localhost:8000/swagger-ui.html

### Script para instalar e executar Local.
    #!/usr/bin/env sh
    
    ## clone project
    git clone https://github.com/JimSP/document-propagation.git
    cd document-propagation
    
    ## build projects
    ./gradlew clean build
    
    ## run microservices
    nohup ./gradlew document-propagation-server:bootRun &
    nohup ./gradlew document-propagation-example-hello:bootRun &
    nohup ./gradlew document-propagation-example-world:bootRun &
    
    ### open browser
    x-www-browser http://localhost:8000/swagger-ui.html
