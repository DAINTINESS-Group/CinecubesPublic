package TaskMgr;

import java.sql.ResultSet;

public abstract class ExtractionMethod {
    public Result Res;
    
    public ExtractionMethod(){
    	Res=new Result();
    }

    abstract public boolean setResult(ResultSet resultSet);
    abstract public Result getResult();
    abstract public String returnMethodString();
    abstract public boolean compareExtractionMethod(ExtractionMethod toCompare);
    
}
