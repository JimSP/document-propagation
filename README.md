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
    
    echo '======================================================================'
    echo '          _                                              _            '
    echo '       __| |  ___    ___  _   _  _ __ ___    ___  _ __  | |_          '
    echo '      / _` | / _ \  / __|| | | || '_ ` _ \  / _ \| '_ \ | __|         '
    echo '     | (_| || (_) || (__ | |_| || | | | | ||  __/| | | || |_          '
    echo '      \__,_| \___/  \___| \__,_||_| |_| |_| \___||_| |_| \__|         '
    echo '                                                                      '
    echo ' _ __   _ __   ___   _ __    __ _   __ _   __ _ | |_ (_)  ___   _ __  '
    echo '| '_ \ | '__| / _ \ | '_ \  / _` | / _` | / _` || __|| | / _ \ | '_ \ '
    echo '| |_) || |   | (_) || |_) || (_| || (_| || (_| || |_ | || (_) || | | |'
    echo '| .__/ |_|    \___/ | .__/  \__,_| \__, | \__,_| \__||_| \___/ |_| |_|'
    echo '|_|                 |_|            |___/                              '
    echo '                                                                      '
    echo 'version: 0.0.1                                                        '
    echo 'release date: 29-05-2019                                              '
    echo '======================================================================
    
    ##clone project
    git clone https://github.com/JimSP/document-propagation.git
    
    ##build project
    cd document-propagation
    ./gradlew distDocker
    
    ##start microservices
    docker-compose up -d
    
    ### open browser
    x-www-browser http://localhost:8000/swagger-ui.html
