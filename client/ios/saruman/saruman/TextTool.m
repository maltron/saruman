//
//  TextTools.m
//  saruman
//
//  Created by Mauricio Leal on 2/28/14.
//  Copyright (c) 2014 Mauricio "Maltron" Leal. All rights reserved.
//

#import "TextTool.h"

@implementation TextTool

+(UITextField *)textField {
    UITextField *field = [[UITextField alloc] init];
    // set all the necessary properties on the TextField
    [field setTranslatesAutoresizingMaskIntoConstraints:NO];
    [field setTextAlignment:NSTextAlignmentLeft];
    [field setAutocapitalizationType:UITextAutocapitalizationTypeWords];
    [field setAutocorrectionType:UITextAutocorrectionTypeNo];
    [field setKeyboardType:UIKeyboardTypeDefault];
    [field setReturnKeyType:UIReturnKeyDefault];
    [field setClearButtonMode:UITextFieldViewModeWhileEditing];
    
    return field;
}

+(UILabel *)label:(NSString *)title {
    UILabel *label = [[UILabel alloc] init];
    [label setTranslatesAutoresizingMaskIntoConstraints:NO];
    [label setText:title];
    [label setTextAlignment:NSTextAlignmentRight];

    
    return label;
}

@end
