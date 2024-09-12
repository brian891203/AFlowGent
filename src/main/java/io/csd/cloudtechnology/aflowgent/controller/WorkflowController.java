// package io.csd.cloudtechnology.aflowgent.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import io.csd.cloudtechnology.aflowgent.dtoRequest.QuestionCategoryRequest;
// import io.csd.cloudtechnology.aflowgent.model.QuestionCategory;
// import io.csd.cloudtechnology.aflowgent.service.QuestionClassifierService;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/workflow")
// public class WorkflowController {

//     @Autowired
//     private QuestionClassifierService questionClassifierService;

//     @PostMapping("/deploy")
//     public ResponseEntity<QuestionCategory> createProduct(@RequestBody @Valid QuestionCategoryRequest request) {
//         Integer questionId = questionClassifierService.createQuestionCategory(request);

//         QuestionCategory questionCategory = questionClassifierService.getQuestionCategoryById(questionId);

//         return ResponseEntity.status(HttpStatus.CREATED).body(questionCategory);
//     }

//     @GetMapping("/question/{employId}")
//     public ResponseEntity<QuestionCategory> getQuestionCategoryByEmpId(@PathVariable("employId") String employId) {
//         QuestionCategory questionCategory = questionClassifierService.getQuestionCategoryByEmpId(employId);

//         if (questionCategory != null) {
//             return ResponseEntity.status(HttpStatus.OK).body(questionCategory);
//         } else {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }
//     }

//     @PutMapping("/question/{employId}")
//     public ResponseEntity<QuestionCategory> updateQuestionCategory(@PathVariable("employId") String employId,
//                                                  @RequestBody @Valid QuestionCategoryRequest request) {

//         QuestionCategory questionCategory = questionClassifierService.getQuestionCategoryByEmpId(employId);

//         if (questionCategory == null) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//         }

//         // update the new question description
//         questionClassifierService.updateCategory(employId, request);

//         QuestionCategory updatedQuestionCategory = questionClassifierService.getQuestionCategoryByEmpId(employId);

//         return ResponseEntity.status(HttpStatus.OK).body(updatedQuestionCategory);
//     }
// }
