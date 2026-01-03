import React, {useState} from "react";
import FormInput from "../../../components/ui/form/FormInput";
import RichTextEditor from "../../../components/ui/RichTextEditor";
import FormSelect from "../../../components/ui/form/FormSelect";
import PrimaryButton from "../../../components/ui/button/PrimaryButton";
import SecondTitle from "../../../components/ui/title/SecondTitle";
import PrimaryTitle from "../../../components/ui/title/PrimaryTitle";
import {getRegionsNotPagination} from "../../../services/regionService";
import {getJobTypesNotPagination} from "../../../services/jobTypeService";
import {useList} from "../../../hooks/useList";
import {getIndustriesNotPagination} from "../../../services/industryService";
import {getLevelsNotPagination} from "../../../services/levelService";
import {createJobPost} from "../../../services/jobPostService";
import {useNavigate} from "react-router-dom";
import {SALARY_TYPE, STATUS_JOB_POST} from "../../../config/constants";
import {getSkillsNotPagination} from "../../../services/skillService.jsx";
import SkillList from "../../../components/ui/form/SkillList.jsx";

const AddJobPost = () => {
    const [formJobPost, setFormJobPost] = useState({
        title: "",
        description: "",
        quantity: 0,

        salary: 0,
        salaryType: null,
        status: "PUBLIC",
        endedTime: "",
        industryId: null,
        levelId: null,
        jobTypeId: null,
        regionId: null,
        skillIds: [],
    });
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();
    const regions = useList(getRegionsNotPagination);
    const jobTypes = useList(getJobTypesNotPagination);
    const industries = useList(getIndustriesNotPagination);
    const levels = useList(getLevelsNotPagination);
    const skills = useList(getSkillsNotPagination);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormJobPost((prev) => ({...prev, [name]: value}));
    };

    const validate = () => {
        const newErrors = {};

        // 1. Các field không được rỗng
        if (!formJobPost.title.trim())
            newErrors.title = "Tiêu đề không được để trống";
        if (!formJobPost.description.trim())
            newErrors.description = "Mô tả không được để trống";
        if (!formJobPost.industryId)
            newErrors.industryId = "Vui lòng chọn ngành nghề";
        if (!formJobPost.levelId) newErrors.levelId = "Vui lòng chọn cấp bậc";
        if (!formJobPost.jobTypeId) newErrors.jobTypeId = "Vui lòng chọn loại nghề";
        if (!formJobPost.regionId) newErrors.regionId = "Vui lòng chọn khu vực";
        if (!formJobPost.salaryType)
            newErrors.salaryType = "Vui lòng chọn loại lương";

        // 2. Số lượng >= 1
        if (formJobPost.quantity < 1)
            newErrors.quantity = "Số lượng phải lớn hơn hoặc bằng 1";

        // 3. Mức lương > 0 và salaryMin < salaryMax
        if (formJobPost.salary <= 0)
            newErrors.salaryMin = "Mức lương từ phải lớn hơn 0";

        // 4. Hạn nộp hồ sơ (datetime) phải > hiện tại
        if (!formJobPost.endedTime) {
            newErrors.endedTime = "Vui lòng chọn hạn nộp hồ sơ";
        } else {
            const endedTimeDate = new Date(formJobPost.endedTime);
            const now = new Date();
            if (endedTimeDate <= now) {
                newErrors.endedTime = "Hạn nộp hồ sơ phải lớn hơn thời gian hiện tại";
            }
        }

        setErrors(newErrors);

        // Nếu không có lỗi → trả về true
        return Object.keys(newErrors).length === 0;
    };


    const handleSkillsChange = (e) => {
        const skillId = Number(e?.target?.value ?? e);
        if (!skillId) return;

        setFormJobPost(prev => {
            if (prev.skillIds.includes(skillId)) {
                return prev;
            }
            return {
                ...prev,
                skillIds: [...prev.skillIds, skillId]
            };
        });
    };

    const handleRemoveSkill = (skillId) => {
        setFormJobPost(prev => ({
            ...prev,
            skillIds: prev.skillIds.filter(id => id !== skillId)
        }));
    };


    const handleSubmit = async () => {
        if (!validate()) {
            console.log("Form có lỗi:", errors);
            return;
        }
        try {
            await createJobPost(formJobPost);
            console.log("Tạo job post thành công:");
            navigate("/");
        } catch (error) {
            alert(error.response?.data?.message || "Đăng ký tài khoản thất bại!");
        }
        // Call API tạo job post
    };

    return (
        <div>
            <div className="p-1 bg-white rounded-2xl my-3">
                <PrimaryTitle>Đăng tin tuyển dụng</PrimaryTitle>
            </div>
            <div className="flex gap-3">
                <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl">
                    <SecondTitle>Thông tin bài đăng</SecondTitle>
                    <FormInput
                        label="Tiêu đề"
                        name="title"
                        value={formJobPost.title}
                        error={errors.title}
                        onChange={handleChange}
                    />
                    <RichTextEditor
                        label={"Mô tả công việc"}
                        value={formJobPost.description}
                        error={errors.description}
                        onChange={(value) =>
                            setFormJobPost((prev) => ({...prev, description: value}))
                        }
                        placeholder={"Mô tả công việc"}
                    />
                    <FormInput
                        label="Số lượng"
                        name="quantity"
                        type="number"
                        value={formJobPost.quantity}
                        onChange={handleChange}
                        error={errors.quantity}
                    />
                    <FormInput
                        label="Mức lương"
                        name="salary"
                        type="number"
                        value={formJobPost.salary}
                        onChange={handleChange}
                        error={errors.salary}
                    />
                    <FormSelect
                        label="Loại lương"
                        name="salaryType"
                        selected={SALARY_TYPE.find(
                            (j) => j.code === formJobPost.salaryType
                        )}
                        onChange={handleChange}
                        options={SALARY_TYPE}
                        placeholder="Chọn loại lương"
                        error={errors.salaryType}
                    />
                    <FormInput
                        label="Hạn nộp hồ sơ"
                        name="endedTime"
                        type="datetime-local"
                        value={formJobPost.endedTime}
                        onChange={handleChange}
                        error={errors.endedTime}
                    />
                    {/* Trạng thái bài đăng */}
                    <FormSelect
                        label="Trạng thái bài đăng"
                        name="status"
                        selected={STATUS_JOB_POST.find(
                            (j) => j.code === formJobPost.status
                        )}
                        onChange={handleChange}
                        options={STATUS_JOB_POST}
                        placeholder="Chọn trạng thái"
                        error={errors.industryId}
                    />
                </div>
                <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl ">
                    <SecondTitle>Danh mục</SecondTitle>

                    <FormSelect
                        label="Ngành nghề"
                        name="industryId"
                        selected={industries.data.find(
                            (j) => j.id === formJobPost.industryId
                        )}
                        onChange={handleChange}
                        options={industries.data}
                        placeholder="Chọn ngành nghề"
                        error={errors.industryId}
                    />

                    <FormSelect
                        label="Cấp bậc"
                        name="levelId"
                        selected={levels.data.find((j) => j.id === formJobPost.levelId)}
                        onChange={handleChange}
                        options={levels.data}
                        placeholder="Chọn cấp bậc"
                        error={errors.levelId}
                    />
                    <FormSelect
                        label="Loại nghề"
                        name="jobTypeId"
                        selected={jobTypes.data.find((j) => j.id === formJobPost.jobTypeId)}
                        onChange={handleChange}
                        options={jobTypes.data}
                        placeholder="Chọn loại nghề"
                        error={errors.jobTypeId}
                    />
                    <FormSelect
                        label="Khu vực"
                        name="regionId"
                        selected={regions.data.find((r) => r.id === formJobPost.regionId)}
                        onChange={handleChange}
                        options={regions.data}
                        placeholder="Chọn khu vực"
                        error={errors.regionId}
                    />

                    <div className="mt-3">
                        <SecondTitle></SecondTitle>
                        <span>Kỹ năng</span>
                        {formJobPost.skillIds.length > 0 && (
                            <SkillList
                                skillIds={formJobPost.skillIds}
                                allSkills={skills.data}
                                onRemove={handleRemoveSkill}
                            />
                        )}
                        <div className="mt-3">
                            <FormSelect
                                label="Thêm kỹ năng"
                                selected={null}
                                options={skills.data?.filter(
                                    skill => !formJobPost.skillIds.includes(skill.id)
                                )}
                                placeholder="Chọn kỹ năng để thêm"
                                onChange={handleSkillsChange}
                            />
                        </div>
                    </div>

                    <div className="flex justify-end ">
                        <PrimaryButton onClick={handleSubmit}>
                            Đăng bài tuyển dụng
                        </PrimaryButton>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AddJobPost;

