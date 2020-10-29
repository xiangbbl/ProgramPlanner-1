package com.MyApplicationTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.myapplication.Project;

/**
 * Tests all methods in the Project class
 * See test comments for more details
 * ~/app/src/main/java/com/example/myapplication/Project.java
 */
public class ProjectTest {
    /*
     * <Constructor Tests>
     * The following tests all test one aspect of the constructor.
     * They are very simple in nature, and merely assert that the
     * values of the created object reflect those passed in the constructor.
     * One test for each field.
     * Two objects will be created to make sure static fields are changed properly and non-statics are not.
     * First created object will be used for both kinds, to verify that the field changed or didn't change respectively.
     */

    @Test
    public void testConstructorIProjectId() {
        Project testProject = new Project(5, "blank", "blank", "blank");
        Project testProject1 = new Project(4, "blank", "blank", "blank");
        assertEquals("Project constructor failed to initialize the 'i_projectId' field.", 5, testProject.getProjectId());
    }

    @Test
    public void testConstructorProjectId() {
        Project testProject = new Project(1, "blank", "blank", "blank");
        Project testProject1 = new Project(2, "blank", "blank", "blank");
        assertEquals("Project constructor failed to overwrite the 'projectId' field.", 2, Project.projectId);
    }

    @Test
    public void testConstructorIProjectName() {
        Project testProject = new Project(0, "create Name", "blank", "blank");
        Project testProject1 = new Project(0, "overwrite Name", "blank", "blank");
        assertTrue("Project constructor failed to initialize the 'i_projectName' field.", testProject.getProjectName().equals("create Name"));
    }

    @Test
    public void testConstructorProjectName() {
        Project testProject = new Project(0, "create Name", "blank", "blank");
        Project testProject1 = new Project(0, "overwrite Name", "blank", "blank");
        assertTrue("Project constructor failed to overwrite the 'ProjectName' field.", Project.projectName.equals("overwrite Name"));
    }

    @Test
    public void testConstructorIClientCode() {
        Project testProject = new Project(0, "blank", "create Code", "blank");
        Project testProject1 = new Project(0, "blank", "overwrite Code", "blank");
        assertTrue("Project constructor failed to initialize the 'i_clientCode' field.", testProject.getClientCode().equals("create Code"));
    }

    @Test
    public void testConstructorClientCode() {
        Project testProject = new Project(0, "blank", "create Code", "blank");
        Project testProject1 = new Project(0, "blank", "overwrite Code", "blank");
        assertTrue("Project constructor failed to overwrite the 'ProjectName' field.", Project.clientCode.equals("overwrite Code"));
    }

    @Test
    public void testConstructorIDevCode() {
        Project testProject = new Project(0, "blank", "blank", "create Code");
        Project testProject1 = new Project(0, "blank", "blank", "overwrite Code");
        assertTrue("Project constructor failed to initialize the 'i_clientCode' field.", testProject.getDevCode().equals("create Code"));
    }

    @Test
    public void testConstructorDevCode() {
        Project testProject = new Project(0, "blank", "blank", "create Code");
        Project testProject1 = new Project(0, "blank", "blank", "overwrite Code");
        assertTrue("Project constructor failed to overwrite the 'ProjectName' field.", Project.devCode.equals("overwrite Code"));
    }
    //</Constructor Tests>

    /*
     * <generateCode Tests>
     *  The generate code tests will test different
     *  paths through the function
     * Randomness is difficult to test, so we will instead make sure there are the right number
     * of digits
     */

    @Test
    public void testGenerateCodeIsClient() {
        String generated = Project.generateCode(true);
        assertEquals("Generated code length was not 6.", 6, generated.length());
    }
}
