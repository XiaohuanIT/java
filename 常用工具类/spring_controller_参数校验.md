

#### get请求参数校验

```
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/data")
@Validated
public class DistributorWhiteController {
    @GetMapping
    @ResponseBody
    public Object search(@RequestParam(value = "page_index", required = false, defaultValue = "1") @Min(1) Integer pageIndex,
                         @RequestParam(value = "page_size", required = false, defaultValue = "20") @Min(20) @Max(50) Integer pageSize) throws Exception {
}
```



注意要在类上加 `@Validated`  。





#### post请求参数校验

```
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;


@Data
@ApiModel(description = "分页结果")
public class PageResponse<T> {
    @JsonProperty("page")
    @ApiModelProperty(value = "页码")
    private Integer page;
    @JsonProperty("limit")
    @ApiModelProperty(value = "每页数量")
    private Integer limit;
    @JsonProperty("total")
    @ApiModelProperty(value = "总数量")
    private Long total;
    @JsonProperty("list")
    @ApiModelProperty(value = "数据")
    private List<T> list;
}
```

```
@PostMapping(value = "/data1")
public Object<PageResponse<POObject>> list(@Valid @RequestBody TheRequest request) throws Exception {
    
}
```



```
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
public class TheRequest {

    @JsonProperty("page")
    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能小于0")
    private Integer page;

    @JsonProperty("limit")
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量不能小于1")
    private Integer limit;

    @JsonProperty("state")
    @Range(min = 0, max = 4, message = "状态字段不正确")
    private Integer state;
    
     /**
     * 日期
     */
    @JsonProperty("time")
    @NotNull(message = "日期不能为空")
    @FutureDate(message = "日期不能小于当前时间")
    private Long closingTime;
}
```



##### 自定义的校验器



```
/**
 * 日期不能小于当前时间
 */
@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {
    String message() default "{date must grater than now}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```



```
public class FutureDateValidator implements ConstraintValidator<FutureDate, Long> {
    @Override
    public boolean isValid(Long date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        Date now = new Date();
        if (date > now.getTime()) {
            return true;
        }
        return false;
    }
}
```