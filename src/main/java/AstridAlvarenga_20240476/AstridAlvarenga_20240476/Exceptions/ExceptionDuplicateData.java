package AstridAlvarenga_20240476.AstridAlvarenga_20240476.Exceptions;

import lombok.Getter;

public class ExceptionDuplicateData extends RuntimeException {
    @Getter
    private String duplicateField;
    public ExceptionDuplicateData(String message, String duplicateField)
    {
        super(message);
        this.duplicateField = duplicateField;
    }
    public ExceptionDuplicateData(String duplicateField){
        this.duplicateField = duplicateField;
    }
}
