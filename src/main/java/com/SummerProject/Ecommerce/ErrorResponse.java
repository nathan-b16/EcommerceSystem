package com.SummerProject.Ecommerce;

import java.util.Map;

public record ErrorResponse (
    Map<String, String> errors
){ }
