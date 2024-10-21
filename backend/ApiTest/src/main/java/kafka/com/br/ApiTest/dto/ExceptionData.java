package kafka.com.br.ApiTest.dto;

public record ExceptionData(
        String type,
        String title,
        String detail,
        String instance
) {

    public ExceptionData(String type, String title, String detail, String instance){
        this.type = type;
        this.title = title;
        this.detail = detail;
        this.instance = instance;
    }

}
