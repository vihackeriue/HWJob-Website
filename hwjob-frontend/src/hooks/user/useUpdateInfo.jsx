import {ROLES} from "../../config/roles.jsx";
import {useState} from "react";
import {updateUserInfo} from "../../services/userService.jsx";


export const useUpdateInfo = (formData, auth) => {

    const [loading, setLoading] = useState(false);

    const hasRole = (role) => auth?.roles?.includes(role);

    const buildCommonPayload = () => ({
        fullName: formData.fullName || null,
        email: formData.email || null,
        phone: formData.phone || null,
        summary: formData.summary || null,
        regionId: formData.regionId || null,
    });

    const buildCandidatePayload = () => ({
        dob: formData.dob || null,
        gender: formData.gender || null,
        education: formData.education || null,
        expectSalary: formData.expectSalary || null,
        skillIds: formData.skillIds?.length ? formData.skillIds : null,
    });

    const buildRecruiterPayload = () => ({
        website: formData.website,
    });

    const buildSubmitPayload = () => {
        const common = buildCommonPayload();

        if (hasRole(ROLES.CANDIDATE)) {
            return {
                role: ROLES.CANDIDATE,
                payload: {
                    ...common,
                    ...buildCandidatePayload(),
                },
            };
        }

        if (hasRole(ROLES.RECRUITER)) {
            return {
                role: ROLES.RECRUITER,
                payload: {
                    ...common,
                    ...buildRecruiterPayload(),
                },
            };
        }

        return null;
    };

    const updateProfile = async () => {
        const submitData = buildSubmitPayload();
        if (!submitData) return;

        setLoading(true);

        try {
            await updateUserInfo(
                submitData.payload,
                submitData.role
            );
            return true;
        } finally {
            setLoading(false);
        }
    };
    return {
        updateProfile,
        loading,
    };
};
