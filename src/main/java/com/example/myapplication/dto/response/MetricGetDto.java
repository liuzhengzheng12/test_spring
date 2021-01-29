package com.example.myapplication.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetricGetDto {

	List<List<Object>> metric;

	private List<Integer> summary;

	private Double pay_ratio;

	private Double pay_ratio_first_day;
}
