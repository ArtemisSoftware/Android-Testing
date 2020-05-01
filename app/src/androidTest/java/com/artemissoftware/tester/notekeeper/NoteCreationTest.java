package com.artemissoftware.tester.notekeeper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {


    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityRule = new ActivityTestRule<>(NoteListActivity.class);
}