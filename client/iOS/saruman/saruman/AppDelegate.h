//
//  AppDelegate.h
//  saruman
//
//  Created by Mauricio Leal on 2/25/14.
//  Copyright (c) 2014 Mauricio Leal. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "User.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;

@property (nonatomic, strong) User *currentUser;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;

-(NSString *)server;
-(NSInteger)port;

-(NSURL *)requestUsername:(NSString *)username;

@end
