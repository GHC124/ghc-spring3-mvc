package com.ghc.web.controller;

import static com.ghc.web.Constants.DATE_FORMAT_PATTERN;

import java.beans.PropertyEditorSupport;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class AbsController {
	@Autowired
	protected MessageSource messageSource;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
	}

	public class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
				DateTime dateTime = org.joda.time.format.DateTimeFormat
						.forPattern(DATE_FORMAT_PATTERN).parseDateTime(text);
				setValue(dateTime);
			} else {
				setValue(null);
			}
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null) {
				s = org.joda.time.format.DateTimeFormat.forPattern(
						DATE_FORMAT_PATTERN).print((DateTime) getValue());
			}
			return s;
		}
	}
}