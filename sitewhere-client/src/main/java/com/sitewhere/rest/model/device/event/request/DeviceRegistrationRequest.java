/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.rest.model.device.event.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sitewhere.spi.device.event.request.IDeviceRegistrationRequest;
import com.sitewhere.spi.device.event.state.RegistrationState;
import com.sitewhere.spi.device.event.state.StateChangeCategory;
import com.sitewhere.spi.device.event.state.StateChangeType;

/**
 * Default model implementation of {@link IDeviceRegistrationRequest}.
 * 
 * @author Derek
 */
@JsonIgnoreProperties
@JsonInclude(Include.NON_NULL)
public class DeviceRegistrationRequest extends DeviceStateChangeCreateRequest implements IDeviceRegistrationRequest {

	/** Data map identifier for hardware id */
	public static final String DATA_HARDWARE_ID = "hardwareId";

	/** Data map identifier for specification token */
	public static final String DATA_SPECIFICATION_TOKEN = "specificationToken";

	/** Data map identifier for 'reply to' address */
	public static final String DATA_REPLY_TO = "replyTo";

	public DeviceRegistrationRequest() {
		super(StateChangeCategory.Registration, StateChangeType.Registration,
				RegistrationState.Unregistered.name(), RegistrationState.Registered.name());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.device.event.request.IDeviceRegistrationCreateRequest#getHardwareId
	 * ()
	 */
	@Override
	public String getHardwareId() {
		return getData().get(DATA_HARDWARE_ID);
	}

	public void setHardwareId(String hardwareId) {
		getData().put(DATA_HARDWARE_ID, hardwareId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sitewhere.spi.device.event.request.IDeviceRegistrationCreateRequest#
	 * getSpecificationToken()
	 */
	@Override
	public String getSpecificationToken() {
		return getData().get(DATA_SPECIFICATION_TOKEN);
	}

	public void setSpecificationToken(String specificationToken) {
		getData().put(DATA_SPECIFICATION_TOKEN, specificationToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.device.event.request.IDeviceRegistrationCreateRequest#getReplyTo
	 * ()
	 */
	@Override
	public String getReplyTo() {
		return getData().get(DATA_REPLY_TO);
	}

	public void setReplyTo(String replyTo) {
		getData().put(DATA_REPLY_TO, replyTo);
	}
}