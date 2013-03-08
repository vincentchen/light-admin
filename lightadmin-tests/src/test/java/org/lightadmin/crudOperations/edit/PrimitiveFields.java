package org.lightadmin.crudOperations.edit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lightadmin.SeleniumIntegrationTest;
import org.lightadmin.config.FilterTestEntityConfiguration;
import org.lightadmin.data.Domain;
import org.lightadmin.data.User;
import org.lightadmin.page.EditPage;
import org.lightadmin.page.ListViewPage;
import org.lightadmin.page.LoginPage;
import org.lightadmin.page.ShowViewPage;
import org.springframework.beans.factory.annotation.Autowired;

import static org.lightadmin.util.DomainAsserts.assertFieldValues;

public class PrimitiveFields extends SeleniumIntegrationTest {

	@Autowired
	private LoginPage loginPage;
	private ListViewPage listViewPage;
	private EditPage editPage;

	private ShowViewPage showView;

	@Before
	public void setup() {
		removeAllDomainTypeAdministrationConfigurations();

		registerDomainTypeAdministrationConfiguration( FilterTestEntityConfiguration.class );

		listViewPage = loginPage.get().loginAs( User.ADMINISTRATOR ).navigateToDomain( Domain.FILTER_TEST_DOMAIN );
	}

	@After
	public void cleanup() {
		repopulateDatabase();
	}

	//Covers LA-34: EditView: numeric fields cannot be cleared (https://github.com/max-dev/light-admin/issues/34)
	@Test
	public void canBeCleared() {
		clearAllFieldValuesAndSave();

		String[] showViewFieldValues = showView.getFieldValuesExcludingId();

		assertFieldValues(
				new String[]{ " ", "0", "0", "0", "0", "No" },
				showViewFieldValues );
	}

	@Test
	public void canBeModified() {
		modifyFieldValuesAndSave();

		String[] showViewFieldValues = showView.getFieldValuesExcludingId();

		assertFieldValues(
				new String[]{ "new text value", "1234", "4321", "234.52", "5789.52", "Yes" },
				showViewFieldValues );
	}

	private void clearAllFieldValuesAndSave() {
		editPage = listViewPage.editItem( 4 );

		editPage.clear( "textField" );
		editPage.clear( "integerField" );
		editPage.clear( "primitiveIntegerField" );
		editPage.clear( "decimalField" );

		editPage.check( "booleanField" );

		showView = editPage.submit();
	}

	private void modifyFieldValuesAndSave() {
		editPage = listViewPage.editItem( 3 );

		editPage.type( "textField", "new text value" );
		editPage.type( "integerField", "1234" );
		editPage.type( "primitiveIntegerField", "4321" );
		editPage.type( "decimalField", "234.52" );

		editPage.check( "booleanField" );

		showView = editPage.submit();
	}
}
