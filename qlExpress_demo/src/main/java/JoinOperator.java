import com.ql.util.express.Operator;

public class JoinOperator extends Operator {
    @Override
    public Object executeInner(Object[] list) throws Exception {
        java.util.List result = new java.util.ArrayList();
        Object opdata1 = list[0];
        if(opdata1 instanceof java.util.List){
            result.addAll((java.util.List)opdata1);
        }else{
            result.add(opdata1);
        }
        for(int i=1;i<list.length;i++){
            result.add(list[i]);
        }
        return result;
    }
}
