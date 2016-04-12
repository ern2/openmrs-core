/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module;

import org.openmrs.test.BaseContextSensitiveTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.io.File;

/**
 * Tests Module methods
 */
public class ModuleTest extends BaseContextSensitiveTest {

	private Module mockModule;
	
	@Before
	public void before() throws Exception {
		String omodPath = "org/openmrs/module/include/test1-1.0-SNAPSHOT.omod";

		runtimeProperties.setProperty(ModuleConstants.RUNTIMEPROPERTY_MODULE_LIST_TO_LOAD, omodPath);
		ModuleUtil.startup(runtimeProperties);

		mockModule = ModuleFactory.getModuleById("test1");
	}

	/*
	 * @verifies not expand extensionNames if extensionNames is null
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandExtensionNamesIfExtensionNamesIsNull() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();

		Extension mockExtension = new MockExtension();
		extensions.add(mockExtension);

		mockModule.setExtensions(extensions);
		mockModule.setExtensionNames(null);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
         * @verifies not expand extensionNames if extensionNames is empty
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandExtensionNamesIfExtensionNamesIsEmpty() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();

		Extension mockExtension = new MockExtension();
		extensions.add(mockExtension);

		mockModule.setExtensions(extensions);
		mockModule.setExtensionNames(new IdentityHashMap<String, String>());
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
         * @verifies not expand extensionNames if extensions matches extensionNames
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandExtensionNamesIfExtensionsMatchesExtensionNames() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();
		IdentityHashMap<String, String> extensionNames = new IdentityHashMap<String, String>();

		Extension mockExtension = new MockExtension();
		mockExtension.setPointId("1");
		extensions.add(mockExtension);
		extensionNames.put("1", mockExtension.getClass().getName());

		mockModule.setExtensions(extensions);
		mockModule.setExtensionNames(extensionNames);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
         * @verifies expand extensionNames if extensions does not match extensionNames
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldExpandExtensionNamesIfExtensionsDoesNotMatchExtensionNames() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();
		IdentityHashMap<String, String> extensionNames = new IdentityHashMap<String, String>();

		Extension mockExtension = new MockExtension();
		mockExtension.setPointId("1");
		extensions.add(mockExtension);
		extensionNames.put("2", mockExtension.getClass().getName());

		mockModule.setExtensions(extensions);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		//getExtensionNames will return an empty extensions list since MockExtension won't
		//have a valid class loader
		assertEquals(0, ret.size());
	}

	private class MockExtension extends Extension {
		public Extension.MEDIA_TYPE getMediaType() {
			return null;
		}
	}
}
