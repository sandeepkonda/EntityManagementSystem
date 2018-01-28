package com.ems.app.manager.api;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.ems.app.object.BaseResource;

@Contract
public interface ResourceManager<T extends BaseResource> {
	List<T> search() throws Exception;
}