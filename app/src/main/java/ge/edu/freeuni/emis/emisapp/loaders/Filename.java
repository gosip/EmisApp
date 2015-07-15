package ge.edu.freeuni.emis.emisapp.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingListener;
import ge.edu.freeuni.emis.emisapp.interfaces.InfoUpdatingSubject;
import ge.edu.freeuni.emis.emisapp.interfaces.UpdateMessage;
import ge.edu.freeuni.emis.emisapp.loaders.enums.GradeJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.StudentInfoJSONType;
import ge.edu.freeuni.emis.emisapp.loaders.enums.TranscriptJSONType;
import ge.edu.freeuni.emis.emisapp.model.*;
import ge.edu.freeuni.emis.emisapp.model.Class;
import ge.edu.freeuni.emis.emisapp.model.grading.Category;
import ge.edu.freeuni.emis.emisapp.model.grading.Grade;
import ge.edu.freeuni.emis.emisapp.model.grading.SingleDetailedGrade;

public abstract class Filename {
    public static final String PERSONAL_INFO_FILE = "personal_info.json";
    public static final String GRADES_FILE = "grades.json";
    public static final String TRANSCRIPT_FILE = "transcript.json";
}
