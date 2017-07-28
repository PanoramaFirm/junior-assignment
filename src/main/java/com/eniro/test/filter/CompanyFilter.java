package com.eniro.test.filter;

import com.eniro.test.model.company.Company;

public interface CompanyFilter {
    boolean matches(final Company company);
}
