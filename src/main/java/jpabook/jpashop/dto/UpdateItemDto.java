package jpabook.jpashop.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UpdateItemDto {

    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public UpdateItemDto(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

}
