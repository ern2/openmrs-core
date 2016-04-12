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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Tests Module methods
 */
public class ModuleTest {

	Module mockModule;
	
	@Before
	public void before() {
		mockModule = new Module("mockmodule", "mockmodule", "org.openmrs.module.mockmodule", "author", "description", "1.0");
	}

	/*
	 * @verifies 
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandWhenExtensionNamesIsNull() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();

		Extension mockExtension = new MockExtension();
		extensions.add(mockExtension);

		mockModule.setExtensions(extensions);
		mockModule.serExtensionNames(null);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
	 * @verifies
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandWhenExtensionNamesIsEmpty() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();

		Extension mockExtension = new MockExtension();
		extensions.add(mockExtension);

		mockModule.setExtensions(extensions);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
	 * @verifies
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldNotExpandWhenExtensionsMatchesExtensionNames() {
		ArrayList<Extension> extensions = new ArrayList<Extension>();
		IdentityHashMap<String, String> extensionNames = new IdentitHashMap<String, String>();

		Extension mockExtension = new MockExtension();
		mockExtension.setPointId("1");
		extensions.add(mockExtension);
		extensionNames.put("1", mockExtension.getClass.getName());

		mockModule.setExtensions(extensions);
		mockModuel.setExtensionNames(extensionNames);
		ArrayList<Extension> ret = new ArrayList<Extension>(mockModule.getExtensions());

		assertEquals(extensions.get(0), ret.get(0));
		assertEquals(1, ret.size());
	}

	/*
	 * @verifies
	 * @see Module#getExtensions()
	 */
	@Test
	public void getExtensions_shouldExpandWhenExtensionsDoesNotMatchExtensionNames() {
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
