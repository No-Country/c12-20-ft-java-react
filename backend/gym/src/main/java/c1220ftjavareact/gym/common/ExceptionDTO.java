package c1220ftjavareact.gym.common;

import lombok.Builder;

@Builder
public record ExceptionDTO(
        String title,
        String detail,
        String type,
        Integer status
){

}
