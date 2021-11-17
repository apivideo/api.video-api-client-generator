package video.api.client.example

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.preference.EditTextPreference
import androidx.preference.SwitchPreference


class SettingsFragment : PreferenceFragmentCompat() {
    private val filePreference: Preference by lazy {
        findPreference(getString(R.string.file_chooser_key))!!
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filePreference.setOnPreferenceClickListener {
            Intent(Intent.ACTION_PICK).run {
                type = "video/mp4"
                pickFileResult.launch(this)
            }
            true
        }
        val filePath = Utils.getFilePathFromPrefs(requireActivity()) ?: ""
        filePreference.summary = filePath.substring(filePath.lastIndexOf("/") + 1)

        (findPreference(getString(R.string.auth_type_key)) as SwitchPreference?)!!.apply {
            (findPreference(getString(R.string.api_key_key)) as EditTextPreference?)!!.isVisible =
                isChecked
            (findPreference(getString(R.string.video_id_key)) as EditTextPreference?)!!.isVisible =
                isChecked
            (findPreference(getString(R.string.token_key)) as EditTextPreference?)!!.isVisible =
                !isChecked

            setOnPreferenceChangeListener { _, newValue ->
                (findPreference(getString(R.string.api_key_key)) as EditTextPreference?)!!.isVisible =
                    newValue as Boolean
                (findPreference(getString(R.string.video_id_key)) as EditTextPreference?)!!.isVisible =
                    newValue
                (findPreference(getString(R.string.token_key)) as EditTextPreference?)!!.isVisible =
                    !newValue
                true
            }
        }
    }

    private var pickFileResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            requireActivity().getPreferences(Context.MODE_PRIVATE).edit {
                val filePath = Utils.getFilePath(requireContext(), result.data?.data!!) ?: ""
                filePreference.summary = filePath.substring(filePath.lastIndexOf("/") + 1)
                putString(getString(R.string.file_path_key), filePath)
            }
        }
}