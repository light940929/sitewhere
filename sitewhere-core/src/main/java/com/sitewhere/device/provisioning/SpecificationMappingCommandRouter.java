/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.device.provisioning;

import java.util.HashMap;
import java.util.Map;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.IDevice;
import com.sitewhere.spi.device.IDeviceAssignment;
import com.sitewhere.spi.device.IDeviceNestingContext;
import com.sitewhere.spi.device.command.IDeviceCommandExecution;
import com.sitewhere.spi.device.command.ISystemCommand;
import com.sitewhere.spi.device.provisioning.ICommandDestination;
import com.sitewhere.spi.device.provisioning.IOutboundCommandRouter;

/**
 * Implementation of {@link IOutboundCommandRouter} that maps specification ids to
 * {@link ICommandDestination} ids and routes accordingly.
 * 
 * @author Derek
 */
public class SpecificationMappingCommandRouter extends OutboundCommandRouter {

	/** Map of specification tokens to command destination ids */
	private Map<String, String> mappings = new HashMap<String, String>();

	/** Default destination for unmapped specifications */
	private String defaultDestination = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.device.provisioning.IOutboundCommandRouter#routeCommand(com.sitewhere
	 * .spi.device.command.IDeviceCommandExecution,
	 * com.sitewhere.spi.device.IDeviceNestingContext,
	 * com.sitewhere.spi.device.IDeviceAssignment, com.sitewhere.spi.device.IDevice)
	 */
	@Override
	public void routeCommand(IDeviceCommandExecution execution, IDeviceNestingContext nesting,
			IDeviceAssignment assignment, IDevice device) throws SiteWhereException {
		ICommandDestination<?, ?> destination = getDestinationForDevice(device);
		destination.deliverCommand(execution, nesting, assignment, device);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.device.provisioning.IOutboundCommandRouter#routeSystemCommand
	 * (com.sitewhere.spi.device.command.ISystemCommand,
	 * com.sitewhere.spi.device.IDeviceNestingContext,
	 * com.sitewhere.spi.device.IDeviceAssignment, com.sitewhere.spi.device.IDevice)
	 */
	@Override
	public void routeSystemCommand(ISystemCommand command, IDeviceNestingContext nesting,
			IDeviceAssignment assignment, IDevice device) throws SiteWhereException {
		ICommandDestination<?, ?> destination = getDestinationForDevice(device);
		destination.deliverSystemCommand(command, nesting, assignment, device);
	}

	/**
	 * Get {@link ICommandDestination} for device based on specification token associated
	 * with the device.
	 * 
	 * @param device
	 * @return
	 * @throws SiteWhereException
	 */
	protected ICommandDestination<?, ?> getDestinationForDevice(IDevice device) throws SiteWhereException {
		String specToken = device.getSpecificationToken();
		String destinationId = mappings.get(specToken);
		if (destinationId == null) {
			if (getDefaultDestination() != null) {
				destinationId = getDefaultDestination();
			} else {
				throw new SiteWhereException("No command destination mapping for specification: " + specToken);
			}
		}
		ICommandDestination<?, ?> destination = getDestinations().get(destinationId);
		if (destination == null) {
			throw new SiteWhereException("No destination found for destination id: " + destinationId);
		}
		return destination;
	}

	public Map<String, String> getMappings() {
		return mappings;
	}

	public void setMappings(Map<String, String> mappings) {
		this.mappings = mappings;
	}

	public String getDefaultDestination() {
		return defaultDestination;
	}

	public void setDefaultDestination(String defaultDestination) {
		this.defaultDestination = defaultDestination;
	}
}